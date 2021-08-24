
function initializeCoreMod() {

    var ASMAPI = Java.type('net.minecraftforge.coremod.api.ASMAPI');

    // Locations are relative to the resources folder. Not the current file.
    ASMAPI.loadFile('javascript/jsasmhelper.js');

    var logger = jsASMHelper.logger;

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
                'class': 'net.minecraft.client.renderer.entity.player.PlayerRenderer',
                'methodName': ASMAPI.mapMethod('m_117775_'), // renderHand
                'methodDesc': '(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/model/geom/ModelPart;)V'
            },
            /**
             * @param method {MethodNode}
             * @returns {MethodNode}
             */
            'transformer': function(methodNode) {

                logger.info("Altering first person hand to support transparent skins.");

                //logger.info("BEFORE")
                //jsASMHelper.printNode(methodNode);

                var entitySolid = ASMAPI.mapMethod("m_110446_"); // entitySolid

                jsASMHelper.eachInstruction(methodNode, function(instructionNode) {
                    var newFunctionCall = null;
                    if(instructionNode.name === entitySolid) {
                        newFunctionCall = ASMAPI.mapMethod("m_110473_") // entityTranslucent
                    }
                    if(newFunctionCall != null) {
                        var methodCall = jsASMHelper.class("net.minecraft.client.renderer.RenderType")
                            .method(newFunctionCall)
                            .desc('(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;')
                            .buildMethodCall();
                        logger.info("BEFORE")
                        jsASMHelper.printInst(instructionNode);
                        logger.info("AFTER")
                        jsASMHelper.printInst(methodCall);
                        methodNode.instructions.insertBefore(instructionNode, methodCall);
                        //jsASMHelper.printNode(methodNode);
                        methodNode.instructions.remove(instructionNode);
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
