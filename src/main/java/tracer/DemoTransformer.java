package tracer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.logging.Logger;

public class DemoTransformer implements ClassFileTransformer {
  /** The normal form class name of the class to transform */
	protected String className;
	/** The class loader of the class */
	protected ClassLoader classLoader;
	/** The method name */
	protected String methodName;
	/** The method signature */
	protected String methodSignature;
    private static Logger logger = Logger.getLogger(String.valueOf(AgentInstaller.class));

    public DemoTransformer() {
    }

    /**
	 * Creates a new tracer.DemoTransformer
	 * @param classLoader The classloader to match
	 * @param className The binary class name of the class to transform
	 * @param methodName The method name
	 * @param methodSignature A regular expression matching the method signature
	 */


	public DemoTransformer(ClassLoader classLoader, String className, String methodName, String methodSignature) {
        logger.info("---------Call tracer.DemoTransformer---------------");
		this.className = className.replace('.', '/');
		this.classLoader = classLoader;
		this.methodName = methodName;
		this.methodSignature = methodSignature;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.instrument.ClassFileTransformer#transform(java.lang.ClassLoader, java.lang.String, java.lang.Class, java.security.ProtectionDomain, byte[])
	 */
	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		System.out.println("Examining class [" + className + "]");
		if(loader.equals(classLoader)) {
			System.out.println("Instrumenting class [" + className + "]");
			return ModifyMethodTest.instrument(className, methodName, methodSignature, loader, classfileBuffer);
		}
		return classfileBuffer;
	}

}

/*if(className.equals(this.className)  && loader.equals(classLoader)) {
			System.out.println("Instrumenting class [" + className + "]");
			return ModifyMethodTest.instrument(className, methodName, methodSignature, loader, classfileBuffer);
		}
		return classfileBuffer;
	}

}
*/

