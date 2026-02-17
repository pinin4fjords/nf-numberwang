package numberwang.plugin

import spock.lang.Specification

class NumberwangExtensionTest extends Specification {

    def 'isNumberwang should return a boolean'() {
        given:
        def ext = new NumberwangExtension()

        expect: 'it returns true or false (both are valid Numberwang outcomes)'
        ext.isNumberwang(42) instanceof Boolean
        ext.isNumberwang(7) instanceof Boolean
        ext.isNumberwang(0) instanceof Boolean
    }

    def 'isNumberwang should produce both outcomes over many attempts'() {
        given: 'enough attempts, both true and false should appear'
        def ext = new NumberwangExtension()
        def results = (1..100).collect { ext.isNumberwang(it) }

        expect:
        results.contains(true)
        results.contains(false)
    }

    def 'checkNumberwang should return an adjudication string'() {
        given:
        def ext = new NumberwangExtension()

        when:
        def result = ext.checkNumberwang(42)

        then:
        result == "That's Numberwang!" || result == "Sorry, 42 is not Numberwang."
    }

    def 'wangernumb should return a number between 0 and 99'() {
        given:
        def ext = new NumberwangExtension()

        when:
        def result = ext.wangernumb(10)

        then:
        result instanceof Number
        result >= 0
        result < 100
    }

    def 'should rotate the board'() {
        given:
        def ext = new NumberwangExtension()

        expect:
        ext.rotateBoard(90) == 90
        ext.rotateBoard(90) == 180
        ext.rotateBoard(180) == 0
    }
}
