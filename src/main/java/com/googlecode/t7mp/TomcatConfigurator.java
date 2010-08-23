package com.googlecode.t7mp;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.IOUtil;

/**
 * Helper to build the tomcat directory structure and use default config files.
 * 
 */
public class TomcatConfigurator {
	
	private final File catalinaBaseDir;
	private final Log log;
	
	public TomcatConfigurator(File catalinaBaseDir, Log log){
		this.catalinaBaseDir = catalinaBaseDir;
		this.log = log;
	}
	
	public TomcatConfigurator createTomcatDirectories() throws MojoExecutionException {
		if(!catalinaBaseDir.exists() && !catalinaBaseDir.mkdir()){
			throw new MojoExecutionException("could not create 'catalina.base' on " + catalinaBaseDir.getAbsolutePath());
		}
		createTomcatDirectory("conf");
		createTomcatDirectory("webapps");
		createTomcatDirectory("lib");
		createTomcatDirectory("temp");
		createTomcatDirectory("work");
		createTomcatDirectory("logs");
		return this;
	}
	
	protected void createTomcatDirectory(String name) throws MojoExecutionException{
		File directory = new File(catalinaBaseDir, name);
		if(!directory.exists() && !directory.mkdir()){
			throw new MojoExecutionException("could not create '" + name + "' on " + directory.getAbsolutePath());
		}
	}
	
	public TomcatConfigurator copyDefaultConfig() throws MojoExecutionException {
		copyConfigResource("catalina.policy");
		copyConfigResource("catalina.properties");
		copyConfigResource("context.xml");
		copyConfigResource("logging.properties");
		copyConfigResource("server.xml");
		copyConfigResource("tomcat-users.xml");
		copyConfigResource("web.xml");
		return this;
	}
	
	protected void copyConfigResource(String name) throws MojoExecutionException{
		try {
			IOUtil.copy(getClass().getResourceAsStream("conf/" + name), new FileOutputStream(new File(catalinaBaseDir, "/conf/" + name)));
		} catch (FileNotFoundException e) {
			throw new MojoExecutionException(e.getMessage(),e);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(),e);
		}
	}
	
	public void copyUserConfigs(File userConfigDir) throws MojoExecutionException {
		if(userConfigDir == null){
			log.info("No directory for userConfigFiles configured.");
			return;
		}
		if(!userConfigDir.exists() || !userConfigDir.isDirectory()) { 
			log.warn("The configured Directory for configuration files does not exist. " + userConfigDir.getAbsolutePath());
			log.warn("Ignoring user configuration.");
		}
		if(userConfigDir.exists() && userConfigDir.isDirectory()) {
			File[] files = userConfigDir.listFiles(new FilesOnlyFileFilter());
			for(File configFile : files){
				try {
					IOUtil.copy(new FileInputStream(configFile), new FileOutputStream(new File(catalinaBaseDir, "/conf/" + configFile.getName())));
				} catch (FileNotFoundException e) {
					throw new MojoExecutionException(e.getMessage(), e);
				} catch (IOException e) {
					throw new MojoExecutionException(e.getMessage(), e);
				}
			}
		}
	}

}
