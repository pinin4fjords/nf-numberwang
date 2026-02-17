package numberwang.plugin

import nextflow.Session
import spock.lang.Specification

class NumberwangFactoryTest extends Specification {

    def 'should create the observer when enabled'() {
        given:
        def factory = new NumberwangFactory()
        def session = Stub(Session) {
            getConfig() >> [numberwang: [enabled: true, rotateBoard: true]]
        }

        when:
        def result = factory.create(session)

        then:
        result.size() == 1
        result.first() instanceof NumberwangObserver
    }
}
