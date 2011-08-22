/**
 * Copyright (C) 2010-2011 Joerg Bellmann <joerg.bellmann@googlemail.com>
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
package com.googlecode.t7mp.steps.tomcat;

import com.googlecode.t7mp.steps.DefaultStepSequence;
import com.googlecode.t7mp.steps.deployment.AddRemoteRepositoryStep;
import com.googlecode.t7mp.steps.deployment.ArtifactDeploymentSequence;
import com.googlecode.t7mp.steps.deployment.CheckT7ArtifactsStep;
import com.googlecode.t7mp.steps.external.ResolveTomcatStep;
import com.googlecode.t7mp.steps.resources.ConfigFilesSequence;
import com.googlecode.t7mp.steps.resources.CopyConfigResourcesFromClasspathSequence;
import com.googlecode.t7mp.steps.resources.CopyProjectWebappStep;
import com.googlecode.t7mp.steps.resources.OverwriteWebXmlStep;
import com.googlecode.t7mp.steps.resources.SetSystemPropertiesStep;

/**
 * TODO Comment.
 * 
 * @author jbellmann
 *
 */
public class TomcatSetupSequence extends DefaultStepSequence {

    public TomcatSetupSequence() {
        this.add(new AddRemoteRepositoryStep());
        this.add(new CheckT7ArtifactsStep());
        this.add(new ResolveTomcatStep());
        //        this.add(new CreateTomcatDirectoriesSequence());
        this.add(new CopyConfigResourcesFromClasspathSequence());
        this.add(new ConfigFilesSequence());
        this.add(new ArtifactDeploymentSequence());
        this.add(new CopyProjectWebappStep());
        this.add(new SetSystemPropertiesStep());
        this.add(new OverwriteWebXmlStep());
    }

}
