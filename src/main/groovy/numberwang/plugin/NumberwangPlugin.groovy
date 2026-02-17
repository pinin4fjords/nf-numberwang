package numberwang.plugin

import groovy.transform.CompileStatic
import nextflow.plugin.BasePlugin
import org.pf4j.PluginWrapper

@CompileStatic
class NumberwangPlugin extends BasePlugin {

    NumberwangPlugin(PluginWrapper wrapper) {
        super(wrapper)
    }
}
