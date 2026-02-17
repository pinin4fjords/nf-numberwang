package numberwang.plugin

import groovy.transform.CompileStatic
import nextflow.Session
import nextflow.trace.TraceObserver
import nextflow.trace.TraceObserverFactory

@CompileStatic
class NumberwangFactory implements TraceObserverFactory {

    @Override
    Collection<TraceObserver> create(Session session) {
        final enabled = session.config.navigate('numberwang.enabled', true)
        if (!enabled) return []

        final rotateBoard = session.config.navigate('numberwang.rotateBoard', true) as boolean
        return [new NumberwangObserver(rotateBoard)]
    }
}
