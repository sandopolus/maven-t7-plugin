/**
 * Copyright (C) 2010-2012 Joerg Bellmann <joerg.bellmann@googlemail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.t7mp;

import com.googlecode.t7mp.configuration.PluginArtifactResolver;
import com.googlecode.t7mp.steps.Context;

/**
 * 
 * @author Joerg Bellmann
 *
 */
public class DefaultMavenPluginContext implements MavenPluginContext {

    protected Context context;
    protected AbstractT7BaseMojo mojo;

    public DefaultMavenPluginContext(Context parent, AbstractT7BaseMojo mojo) {
        this.context = parent;
        this.mojo = mojo;
    }

    @Override
    public PluginLog getLog() {
        return context.getLog();
    }

    @Override
    public PluginArtifactResolver getArtifactResolver() {
        return context.getArtifactResolver();
    }

    @Override
    public T7Configuration getConfiguration() {
        return context.getConfiguration();
    }

    @Override
    public void put(String key, Object value) {
        context.put(key, value);

    }

    @Override
    public Object get(String key) {
        return context.get(key);
    }

    @Override
    public void clear() {
        context.clear();
    }

    @Override
    public AbstractT7BaseMojo getMojo() {
        return this.mojo;
    }

}
