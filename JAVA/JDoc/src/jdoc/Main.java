//*****************************************************************************
package jdoc;
//*****************************************************************************

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

//*****************************************************************************
public class Main {

	//=========================================================================
	private static final String CLASS = ".class";
	private static final String JAR   = ".jar";
	//=========================================================================

	//=========================================================================
	private static void addClassNames(
		List<String> nameList,
		File folder,
		String prefix
	) {
		var folderList = folder.listFiles();
		if (folderList == null) return;
		for (var item : folderList) {			
			if (item.isFile() && item.getName().endsWith(CLASS)) {
				var name = item.getName();
				if (name.contains("$")) continue;
				name = name.substring(0, name.length() - CLASS.length());
				name = prefix + name;
				nameList.add(name);
			} else if (item.isDirectory()) {
				var name = item.getName();
				name = name + ".";
				var newPrefix = prefix + name;
				addClassNames(nameList, item, newPrefix);
			}
		}
	}
	//=========================================================================
	
	//=========================================================================
	private static void addJarClassNames(
		List<String> nameList,
		File jarFile
	) {
		try (var jar = new JarFile(jarFile)) {
			var entries = jar.entries();
			while (entries.hasMoreElements()) {
				var entry = entries.nextElement();
				var name =  entry.getName();
				if (name.contains("$")) continue;
				if (name.endsWith("package-info.class")) continue;
				if (name.endsWith("module-info.class")) continue;
				if (name.startsWith("META-INF/")) continue;
				if (name.endsWith(CLASS)) {
					name = name.replace('/', '.');
					name = name.substring(0, name.length() - CLASS.length());
					nameList.add(name);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//=========================================================================
	
	//=========================================================================
	private static void addSources(
		List<URL> urlList,
		List<String> nameList,
		File file
	) {
		if (file.exists() && file.canRead()) {
			if (file.getName().endsWith(JAR)) {
				try {
					var url = file.toURI().toURL();
					urlList.add(url);
					if (file.isDirectory()) {
						addClassNames(nameList, file, "");
					} else {
						addJarClassNames(nameList, file);
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			} else if (file.isDirectory()) {
				var childList = file.listFiles();
				if (childList != null) {
					for (var child : childList) {
						addSources(urlList, nameList, child);
					}
				}
			}
		}
	}
	//=========================================================================
	
	//=========================================================================
	private static void aggregateSources(
		List<URL> urlList,
		List<String> nameList,
		String ... args
	) {
		for (var arg :args) {
			var file = new File(arg);
			addSources(urlList, nameList, file);
		}
	}
	//=========================================================================

	//=========================================================================
	private static DocumentBuilder createDocumentBuilder() {
		DocumentBuilder builder = null;
		try {
			var factory = DocumentBuilderFactory.newDefaultInstance();
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return builder;
	}
	//=========================================================================	

	//=========================================================================
	private static Transformer createTransformer() {
		Transformer transformer = null;
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			transformer = factory.newTransformer();
		}
		catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		return transformer;
	}
	//=========================================================================	

	//=========================================================================
	private static void aggregatePackageData(
		Map<String, List<String>> packageData,
		Class<?> clazz
	) {
		String packageName = clazz.getPackageName();
		var classList = packageData.get(packageName);
		if (classList == null) {
			classList = new ArrayList<>();
			packageData.put(packageName, classList);
		}
		String className = clazz.getName();
		classList.add(className);
	}
	//=========================================================================
	
	//=========================================================================
	private static void createClassOutputFile(
		DocumentBuilder builder,
		Transformer transformer,
		Class<?> clazz
	) {
		
		var doc = builder.newDocument();

		int depth = clazz.getPackageName().split("\\.").length;
		String xslPath = "./";
		for (var i=0; i<depth; i++) {
			xslPath += "../";
		}
		xslPath += "class.xsl";
		var stylesheetPI = doc.createProcessingInstruction("xml-stylesheet", "type=\"text/xsl\" href=\"" + xslPath + "\"");
		doc.appendChild(stylesheetPI);
		
		var javaClass = doc.createElement("JavaClass");
		javaClass.setAttribute("name", clazz.getName());
		javaClass.setAttribute("simpleName", clazz.getSimpleName());
		javaClass.setAttribute("package", clazz.getPackageName());
		
		for (var method : clazz.getDeclaredMethods()) {
			var javaMethod = doc.createElement("JavaMethod");
			javaMethod.setAttribute("name", method.getName());
			javaMethod.setAttribute("return", method.getReturnType().getName());
			javaMethod.setAttribute("simpleReturn", method.getReturnType().getSimpleName());
			for (var parameter : method.getParameters()) {
				var javaParameter = doc.createElement("JavaParameter");
				javaParameter.setAttribute("name", parameter.getName());
				javaParameter.setAttribute("type", parameter.getParameterizedType().getTypeName());
				javaParameter.setAttribute("simpleType", parameter.getType().getSimpleName());
				javaMethod.appendChild(javaParameter);
			}
			javaClass.appendChild(javaMethod);
		}
		doc.appendChild(javaClass);

		String name = clazz.getName();
		String path = "output/" + name.replace('.', '/') + ".xml";
		File file = new File(path);
		File folder = file.getParentFile();
		if (!folder.exists()) folder.mkdirs();
		writeXMLFile(transformer, doc, file);
		
	}
	//=========================================================================

	//=========================================================================
	private static void createPackagesOutputFile(
		DocumentBuilder builder,
		Transformer transformer,
		Map<String, List<String>> packageData
	) {
		
		var doc = builder.newDocument();
		
		var stylesheetPI = doc.createProcessingInstruction("xml-stylesheet", "type=\"text/xsl\" href=\"packages.xsl\"");
		doc.appendChild(stylesheetPI);
		
		var javaPackages = doc.createElement("JavaPackages");
		for (var packageName : packageData.keySet()) {
			var javaPackage = doc.createElement("JavaPackage");
			javaPackage.setAttribute("name", packageName);
			javaPackages.appendChild(javaPackage);
		}
		doc.appendChild(javaPackages);

		String path = "output/packages.xml";
		File file = new File(path);
		File folder = file.getParentFile();
		if (!folder.exists()) folder.mkdirs();
		writeXMLFile(transformer, doc, file);
		
	}
	//=========================================================================

	//=========================================================================
	private static void createPackageIndexFile(
		DocumentBuilder builder,
		Transformer transformer,
		String packageName,
		List<String> packageClasses
	) {
		
		var doc = builder.newDocument();		

		int depth = packageName.split("\\.").length;
		String xslPath = "./";
		for (var i=0; i<depth; i++) {
			xslPath += "../";
		}
		xslPath += "classes.xsl";
		var stylesheetPI = doc.createProcessingInstruction("xml-stylesheet", "type=\"text/xsl\" href=\"" + xslPath + "\"");
		doc.appendChild(stylesheetPI);
		
		var javaPackage = doc.createElement("JavaPackage");
		javaPackage.setAttribute("name", packageName);
		for (var className : packageClasses) {
			var javaClass = doc.createElement("JavaClass");
			javaClass.setAttribute("name", className);
			var simpleName = className;
			int lastDot = simpleName.lastIndexOf('.');
			if (lastDot >= 0) simpleName=simpleName.substring(lastDot+1);
			javaClass.setAttribute("simpleName", simpleName);
			javaPackage.appendChild(javaClass);
		}
		doc.appendChild(javaPackage);

		String path = "output/";
		path += packageName.replace('.', '/');
		path += "/index.xml";
		File file = new File(path);
		File folder = file.getParentFile();
		if (!folder.exists()) folder.mkdirs();
		writeXMLFile(transformer, doc, file);
		
	}
	//=========================================================================
	
	//=========================================================================
	private static void writeXMLFile(
		Transformer transformer,
		Document document,
		File file
	) {
		try {
			var source = new DOMSource(document);
			var target = new StreamResult(file);
			transformer.transform(source, target);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	//=========================================================================

	//=========================================================================
	private static void deployAssets() {
		writeAssetFile("index.html");
		writeAssetFile("class.xsl");
		writeAssetFile("classes.xsl");
		writeAssetFile("packages.xsl");
	}
	//=========================================================================

	//=========================================================================
	private static void writeAssetFile(String asset) {
		File folder = new File("output");
		folder.mkdirs();
		File file = new File(folder, asset);
		try {
			var os = new FileOutputStream(file);
			var is = ClassLoader.getSystemResourceAsStream(asset);		
			int res = is.read();
			while (res != -1) {
				os.write(res);
				res = is.read();
			}
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//=========================================================================
	
	//=========================================================================
	public static void main(String[] args) {
		
		var builder = createDocumentBuilder();
		var transformer = createTransformer();

		List<URL>    urlList  = new ArrayList<>();
		List<String> nameList = new ArrayList<>();
		Map<String, List<String>> packageData = new HashMap<>();
		
		aggregateSources(urlList, nameList, args);
		var array  = urlList.toArray(new URL[] {});
		var loader = new URLClassLoader(array);
		for (var className : nameList) {
			try {
				var clazz = Class.forName(className, false, loader);
				aggregatePackageData(packageData, clazz);
				createClassOutputFile(builder, transformer, clazz);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		createPackagesOutputFile(builder, transformer, packageData);		
		
		for (var packageName : packageData.keySet()) {
			var list = packageData.get(packageName);
			createPackageIndexFile(builder, transformer, packageName, list);
		}

		deployAssets();
		
	}
	//=========================================================================

}
//*****************************************************************************
