package numberwang.plugin

import nextflow.config.spec.ConfigOption
import nextflow.config.spec.ConfigScope
import nextflow.config.spec.ScopeName
import nextflow.script.dsl.Description

/**
 * Configuration options for the nf-numberwang plugin.
 *
 * Users configure these in nextflow.config:
 *
 *     numberwang {
 *         enabled = true
 *         rotateBoard = true
 *         boardRotation = 0
 *     }
 */
@ScopeName('numberwang')
class NumberwangConfig implements ConfigScope {

    NumberwangConfig() {}

    NumberwangConfig(Map opts) {
        this.enabled = opts.enabled as Boolean ?: true
        this.rotateBoard = opts.rotateBoard as Boolean ?: true
        this.boardRotation = opts.boardRotation as Integer ?: 0
    }

    @ConfigOption
    @Description('Enable or disable the Numberwang experience')
    boolean enabled = true

    @ConfigOption
    @Description('Rotate the board after every 3 Numberwangs')
    boolean rotateBoard = true

    @ConfigOption
    @Description('Initial board rotation offset (affects Colosson formula)')
    int boardRotation = 0
}
