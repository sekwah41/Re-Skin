
function initializeCoreMod() {

    var ASMAPI = Java.type('net.minecraftforge.coremod.api.ASMAPI');

    ASMAPI.log("INFO", "Testing log function");

    // Locations are relative to the resources folder. Not the current file.
    ASMAPI.loadFile('javascript/jsasmhelper.js');

    var logger = jsASMHelper.logger;

    var isDev = true;

    /**
     * You can use the mixin or at reference for help e.g. net.minecraft.client.gui.screen.MainMenuScreen init()V #init
     * Descriptors for the types of target
     * https://github.com/MinecraftForge/CoreMods/blob/e6fed88bfcb29bc062c04310f18ebe2777582d03/src/main/java/net/minecraftforge/coremod/CoreMod.java#L66
     * see net.minecraftforge.coremod.CoreMod.buildCore
     *
     * At time of writing target parameters for each 'type'
     *
     * METHOD
     *     class - string of class name
     *     methodName - string of method name
     *     methodDesc - string of method description
     * CLASS
     *     name - string of class name
     *       or
     *     names - function that takes a list of classes and returns an array of targets
     * FIELD
     *     class - string of class name
     *     fieldName - string of field name
     *
     * transformer - look at org.objectweb.asm for more info
     * METHOD - org.objectweb.asm.tree.MethodNode
     */
    return {
        'first_person_hand': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.client.renderer.entity.PlayerRenderer',
                'methodName': 'func_229145_a_disabled', // renderHand
                'methodDesc': '(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;ILnet/minecraft/client/entity/player/AbstractClientPlayerEntity;Lnet/minecraft/client/renderer/model/ModelRenderer;Lnet/minecraft/client/renderer/model/ModelRenderer;)V'
            },
            /**
             * @param method {MethodNode}
             * @returns {MethodNode}
             */
            'transformer': function(methodNode) {

                logger.info("Altering first person hand to support transparent skins.");

                //logger.info("BEFORE")
                //jsASMHelper.printNode(methodNode);

                jsASMHelper.eachInstruction(methodNode, function(instructionNode) {
                    var newFunctionCall = null;
                    if(instructionNode.name === "entitySolid") {
                        newFunctionCall = "entityTranslucent"
                    } else if(instructionNode.name === "func_228634_a_"){
                        newFunctionCall = "func_228644_e_"
                    }
                    if(newFunctionCall != null) {
                        var methodCall = jsASMHelper.class("net/minecraft/client/renderer/RenderType")
                            .method(newFunctionCall)
                             .desc('(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;')
                            .buildMethodCall();
                        //methodNode.instructions.insertBefore(instructionNode, methodCall);
                        //methodNode.instructions.remove(instructionNode);
                        logger.info("Method call replaced (" + instructionNode.name + " -> " + newFunctionCall + ")");
                    }
                });


                //logger.info("AFTER")
                //jsASMHelper.printNode(methodNode);

                return methodNode;
            },
        },
    }
}
