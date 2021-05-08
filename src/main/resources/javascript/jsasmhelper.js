/**
 * Useful to check
 * https://github.com/MinecraftForge/CoreMods/blob/master/src/main/java/net/minecraftforge/coremod/api/ASMAPI.java
 *
 * For opcodes
 * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html
 * Setup library object to allow easier more clear calling
 *
 */
function setupJSASMHelper (){
    var ASMAPI = Java.type('net.minecraftforge.coremod.api.ASMAPI');

    /**
     * @returns {{warn: warn, debug: debug, error: error, info: info, fatal: fatal}}
     */
    var logger = function() {
        function info(message) {
            ASMAPI.log('INFO', message);
        }
        function warn(message) {
            ASMAPI.log('WARN', message);
        }
        function error(message) {
            ASMAPI.log('ERROR', message);
        }
        function fatal(message) {
            ASMAPI.log('FATAL', message);
        }
        function debug(message) {
            ASMAPI.log('DEBUG', message);
        }
        function trace(message) {
            ASMAPI.log('TRACE', message);
        }
        return {
            info: info,
            warn: warn,
            error: error,
            fatal: fatal,
            debug: debug,
        };
    }();

    /**
     * Possibly rewrite as a json builder so that
     V                          void
     Z                          boolean
     B                          byte
     C                          char
     S                          short
     I                          int
     J                          long
     F                          float
     D                          double
     Lfull/path/Class;          full/path/Class
     */
    function QueryConstructor(clazz) {
        this._class = clazz;
        this._method;
        this._descriptor;
        this._methodType = ASMAPI.MethodType.STATIC;
        this._methodNode;
        this._classNode;

        /**
         * Copies the data so any further edits wont alter the old constructors
         * @return QueryConstructor
         */
        this.clone = function(){
            var qc = new QueryConstructor(this._class);
            qc._method = this._method;
            qc._descriptor = this._descriptor;
            qc._methodType = this._methodType;
            qc._methodNode = this._methodNode;
            qc._classNode = this._classNode;
            return qc;
        }

        // Class
        /**
         * @return QueryConstructor
         */
        this.class = function(clazz) {
            this._class = clazz;
            return this;
        }

        // Method
        /**
         * @return QueryConstructor
         */
        this.method = function(method) {
            this._method = method;
            return this;
        }

        /**
         * @return QueryConstructor
         */
        this.methodType = function(methodType) {
            this._methodType = methodType;
            return this;
        }

        /**
         * @return QueryConstructor
         */
        this.methodNode = function(node) {
            this._methodNode = node;
            return this;
        }
        // Descriptors
        /**
         * @return QueryConstructor
         */
        this.desc = function(descriptor) {
            this._descriptor = descriptor;
            return this;
        }

        /**
         * @return QueryConstructor
         */
        this.classDesc = function(classPath) {
            return this.desc("L" + classPath + ";");
        }

        /**
         * @return QueryConstructor
         */
        this.voidDesc = function() {
            return this.desc("()V");
        }

        /**
         * @return QueryConstructor
         */
        this.boolDesc = function() {
            return this.desc("()Z");
        }

        /**
         * @return QueryConstructor
         */
        this.byteDesc = function() {
            return this.desc("()B");
        }

        /**
         * @return QueryConstructor
         */
        this.charDesc = function() {
            return this.desc("()C");
        }

        /**
         * @return QueryConstructor
         */
        this.shortDesc = function() {
            return this.desc("()S");
        }

        /**
         * @return QueryConstructor
         */
        this.intDesc = function() {
            return this.desc("()I");
        }

        /**
         * @return QueryConstructor
         */
        this.longDesc = function() {
            return this.desc("()L");
        }

        /**
         * @return QueryConstructor
         */
        this.floatDesc = function() {
            return this.desc("()F");
        }

        /**
         * @return QueryConstructor
         */
        this.doubleDesc = function() {
            return this.desc("()D");
        }

        /**
         * @return QueryConstructor
         */
        this.print = function() {
            logger.info("Debug info for QueryConstructor");
            logger.info("class:", this._class);
            logger.info("method:", this._method);
            logger.info("descriptor:", this._descriptor);
            logger.info("methodType:", this._methodType);
            return this;
        }

        // What to do with the data collected.
        /**
         * Needs class, method, descriptor and methodType is optional.
         * @returns {*}
         */
        this.buildMethodCall = function() {
            if(!this._class) logger.error("buildMethodCall: Missing class")
            if(!this._method) logger.error("buildMethodCall: Missing method");
            if(!this._descriptor) logger.error("buildMethodCall: Missing descriptor");
            if(!this._methodType) logger.error("buildMethodCall: Missing methodType");

            if(this._class && this._method && this._descriptor && this._methodType) {
                logger.debug("Building new method.");
                return ASMAPI.buildMethodCall(this._class, this._method, this._descriptor, this._methodType);
            }
        }

        this.insertInto = function(node) {
            node.instructions.insert(this.buildMethodCall());
            return this;
        }

        // TODO add class based methods. Though these should do for now for injecting hooks into places

        /**
         * @param methodCall
         * @param insertMode
         * @return boolean if the method was inserted successfully
         */
        this.insertMethod = function(methodCall, insertMode) {
            return ASMAPI.insertInsnList(this._methodNode, this._methodType, this._class, this._method, this._descriptor,
                ASMAPI.listOf(methodCall), insertMode);

        }

        this.insertMethodBefore = function(methodCall) {
            return this.insertMethod(methodCall, ASMAPI.InsertMode.INSERT_BEFORE);
        }

        this.insertMethodAfter = function(methodCall) {
            return this.insertMethod(methodCall, ASMAPI.InsertMode.INSERT_AFTER);
        }

        this.insertMethodReplace = function(methodCall) {
            return this.insertMethod(methodCall, ASMAPI.InsertMode.REMOVE_ORIGINAL);
        }
    }

    function printIfDefined(label, value) {
        if(value === undefined) {
            logger.info(label + ": undefined");
        }
        else {
            if(value instanceof String) {
                logger.info(label + " " + value);
            }
            else {
                logger.info(label + " " + value.toString());
            }
        }
    }

    /**
     *
     * @param methodNode {MethodNode}
     */
    function outputNodeInstructions(methodNode) {
        eachInstruction(methodNode, function (instruction) {
            logger.info("Instruction")
            printIfDefined("Type", instruction);
            printIfDefined("Opcode", instruction.getOpcode());
            printIfDefined("Name", instruction.name);
            printIfDefined("Desc", instruction.desc);
            if(instruction.next) logger.info(instruction.next.owner);
            logger.info("")
        });
    }

    /**
     *
     * @param methodNode
     * @param callback {function(Instruction)}
     */
    function eachInstruction(methodNode, callback) {
        methodNode.instructions.size()
        var arrayLength = methodNode.instructions.size();
        for (var i = 0; i < arrayLength; ++i) {
            var instruction = methodNode.instructions.get(i);
            callback(instruction);
        }
    }

    /**
     * @return QueryConstructor
     */
    function createQueryConstructor() {
        return new QueryConstructor();
    }

    /**
     * @return QueryConstructor
     */
    function createQueryConstructorWithClass(clazz) {
        return new QueryConstructor(clazz)
    }

    return {
        logger: logger,
        qc: createQueryConstructor,
        class: createQueryConstructorWithClass,
        printNode: outputNodeInstructions,
        eachInstruction: eachInstruction
    };
}

var jsASMHelper = setupJSASMHelper();
