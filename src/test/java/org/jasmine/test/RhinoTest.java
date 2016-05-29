package org.jasmine.test;


import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.javascript.rhino.RhinoTopLevel;
import static org.javascript.rhino.RhinoTopLevel.loadModule;
import org.jasmine.test.MultiThreadedRunner;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mozilla.javascript.Scriptable;

@RunWith(MultiThreadedRunner.class)
public class RhinoTest {

    @Ignore
    @Test
    public void dummy() {

    }    

    
    @Test
    public void rhino_npm_js_test(){
        final ContextFactory contextFactory = new ContextFactory();
       
                
        contextFactory.addListener( new ContextFactory.Listener() {

           @Override
           public void contextCreated(Context cx) {
               System.out.printf( "CONTEXT CREATED [%s] Thread [%d]\n", cx, Thread.currentThread().getId());
           }

           @Override
           public void contextReleased(Context cntxt) {
               System.out.printf( "CONTEXT RELEASED [%s] Thread [%d]\n", cntxt, Thread.currentThread().getId());
           }
        });

        
        contextFactory.call( new ContextAction() {
           
            @Override
            public Object run(Context cx) {
                //final RhinoTopLevelWithNativeRequire topLevel = new RhinoTopLevelWithNativeRequire(cx);
                final RhinoTopLevel topLevel = new RhinoTopLevel(cx);
                
                Scriptable newScope = cx.newObject(topLevel);
                newScope.setPrototype(topLevel);
                newScope.setParentScope(null);
                
                RhinoTopLevel.installNativeRequire(cx, newScope, topLevel);
                RhinoTopLevel.loadModule(cx, newScope, "src/test/javascript/specs/rhino-npm-requireSpec.js");
                
                return newScope;
           }
        });
     
    }

    @Test
    public void rhino_npm_native_test(){
        final ContextFactory contextFactory = new ContextFactory();
       
                
        contextFactory.addListener( new ContextFactory.Listener() {

           @Override
           public void contextCreated(Context cx) {
               System.out.printf( "CONTEXT CREATED [%s] Thread [%d]\n", cx, Thread.currentThread().getId());
           }

           @Override
           public void contextReleased(Context cntxt) {
               System.out.printf( "CONTEXT RELEASED [%s] Thread [%d]\n", cntxt, Thread.currentThread().getId());
           }
        });

        
        contextFactory.call( new ContextAction() {
           
            @Override
            public Object run(Context cx) {
                final RhinoTopLevel topLevel = new RhinoTopLevel(cx);
                
                Scriptable newScope = cx.newObject(topLevel);
                newScope.setPrototype(topLevel);
                newScope.setParentScope(null);
                
                RhinoTopLevel.installNativeRequire(cx, newScope, topLevel);
                loadModule(cx, newScope, "src/test/javascript/specs/rhino-native-requireSpec.js");
                
                return topLevel;
           }
        });
        
        
    }
    
}
