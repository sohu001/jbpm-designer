/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jbpm.designer.web.preprocessing.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.event.Event;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jbpm.designer.web.preprocessing.IDiagramPreprocessingService;
import org.jbpm.designer.web.preprocessing.IDiagramPreprocessingUnit;
import org.jbpm.designer.web.profile.IDiagramProfile;
import org.uberfire.backend.vfs.VFSService;
import org.uberfire.workbench.events.ResourceAddedEvent;
import org.uberfire.workbench.events.ResourceUpdatedEvent;


/**
 * 
 * @author Tihomir Surdilovic
 */
public class PreprocessingServiceImpl implements IDiagramPreprocessingService {

    public static PreprocessingServiceImpl INSTANCE = new PreprocessingServiceImpl();
    private Map<String, IDiagramPreprocessingUnit> _registry = new HashMap<String, IDiagramPreprocessingUnit>();
    
    
    public Collection<IDiagramPreprocessingUnit> getRegisteredPreprocessingUnits(
            HttpServletRequest request) {
        Map<String, IDiagramPreprocessingUnit> preprocessingUnits = new HashMap<String, IDiagramPreprocessingUnit>(_registry);
        return new ArrayList<IDiagramPreprocessingUnit>(preprocessingUnits.values());
    }
    
    public IDiagramPreprocessingUnit findPreprocessingUnit(
            HttpServletRequest request, IDiagramProfile profile) {
        Map<String, IDiagramPreprocessingUnit> preprocessingUnits = new HashMap<String, IDiagramPreprocessingUnit>(_registry);
        return preprocessingUnits.get(profile.getName());
    }
    
    public void init(ServletContext context, VFSService vfsService, Event<ResourceUpdatedEvent> resourceUpdatedEvent, Event<ResourceAddedEvent> resourceAddedEvent) {
        _registry.put("default", new DefaultPreprocessingUnit(context, vfsService, resourceUpdatedEvent, resourceAddedEvent));
        _registry.put("jbpm", new JbpmPreprocessingUnit(context, vfsService, resourceUpdatedEvent, resourceAddedEvent));
    }
    
}
