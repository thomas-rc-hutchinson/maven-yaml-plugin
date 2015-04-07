package com.trch.maven.plugins.test;

import java.io.File;
import java.util.Arrays;
import java.util.List;



import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import com.trch.maven.plugins.ConvertResourcesToYamlMoJo;
import com.trch.maven.plugins.Resource;

public class ConvertResourcesToYamlMojoUTest extends AbstractMojoTestCase {

	private static final String S = File.separator;

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testConvertResourcesToYamlMoJoWithAllConfiguration()
			throws Exception {

		log("testConvertResourcesToYamlMoJoWithAllConfiguration()");

		File pom = new File(getBasedir(),
				"src/test/resources/all_configurations_pom.xml");

		ConvertResourcesToYamlMoJo plugin = (ConvertResourcesToYamlMoJo) lookupMojo(
				"convertResourcesToYaml", pom);

		assertEquals(2, plugin.getResource().size());

		for (Resource r : plugin.getResource()) {
			assertTrue(r
					.getDirectory()
					.getAbsolutePath()
					.endsWith(
							"src" + S + "test" + S + "resources" + S
									+ "test_properties"));

			int count = 1;

			for (String includes : r.getIncludes()) {
				assertEquals("i" + count, includes);
				count++;
			}

			count = 1;

			for (String excludes : r.getExcludes()) {
				assertEquals("e" + count, excludes);
				count++;
			}
		}
	}

	public void testConvertResourcesToYamlMoJoWithNoConfiguration()
			throws Exception {

		log("testConvertResourcesToYamlMoJoWithNoConfiguration()");

		File pom = new File(getBasedir(),
				"src/test/resources/no_configuration_pom.xml");

		ConvertResourcesToYamlMoJo plugin = (ConvertResourcesToYamlMoJo) lookupMojo(
				"convertResourcesToYaml", pom);

		// Inject current directory in as project directory.
		String mockProjectDir = new File("target").getAbsolutePath();
		plugin.setProjectDirectory(mockProjectDir);

		// Tests that default configurations are used
		assertEquals(0, plugin.getResource().size());

	}

	public void testConvertResourcesToYamlMoJoWithNoIncludesOrExcludes()
			throws Exception {

		log("testConvertResourcesToYamlMoJoWithNoIncludesOrExcludes()");

		File pom = new File(getBasedir(),
				"src/test/resources/no_filter_pom.xml");

		ConvertResourcesToYamlMoJo plugin = (ConvertResourcesToYamlMoJo) lookupMojo(
				"convertResourcesToYaml", pom);

		// Tests that default includes and excludes are used
		assertEquals(1, plugin.getResource().size());
		assertEquals(0, plugin.getResource().get(0).getIncludes().size());
		assertEquals(0, plugin.getResource().get(0).getExcludes().size());
	}

		
	private void log(String message) {
		System.out.println("LOG : " + message);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
