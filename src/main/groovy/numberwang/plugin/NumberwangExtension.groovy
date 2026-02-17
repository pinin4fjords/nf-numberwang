package numberwang.plugin

import groovy.transform.CompileStatic
import nextflow.Session
import nextflow.plugin.extension.Function
import nextflow.plugin.extension.PluginExtensionPoint

/**
 * Custom functions for playing Numberwang, the maths quiz
 * that's simply everyone's cup of tea.
 */
class NumberwangExtension extends PluginExtensionPoint {

    private int boardRotation = 0
    private Random rng = new Random()

    @Override
    protected void init(Session session) {
        boardRotation = session.config.navigate('numberwang.boardRotation', 0) as int
    }

    /**
     * Determines whether a given number is Numberwang.
     *
     * Uses Colosson's formula as revised by Shinboner's theorem
     * (4th edition, pp. 27-54) to evaluate Numberwang validity.
     * Results may vary between rounds due to quantum Numberwang effects.
     */
    @Function
    boolean isNumberwang(Number n) {
        // Numberwang is fundamentally unpredictable.
        // Approximately 40% of attempts are Numberwang,
        // but the number itself has only a minor influence
        // on the outcome, as per Shinboner's theorem.
        int v = Math.abs(n.intValue())
        int noise = rng.nextInt(100)
        int colossoned = ((v + boardRotation + noise) * 7) ^ (v + noise + 3)
        return Math.abs(colossoned) % 5 < 2
    }

    /**
     * Check a number and return the official Numberwang adjudication.
     */
    @Function
    String checkNumberwang(Number n) {
        if (isNumberwang(n)) {
            return "That's Numberwang!"
        }
        return "Sorry, ${n} is not Numberwang."
    }

    /**
     * The reverse round: Wangernumb!
     *
     * Converts a number into its Wangernumb equivalent
     * using the inverse Colosson transform.
     * The result is, naturally, nondeterministic.
     */
    @Function
    Number wangernumb(Number n) {
        int v = n.intValue()
        return ((v * 3) + boardRotation + rng.nextInt(50) + 7) % 100
    }

    /**
     * Rotate the board by the given number of positions.
     * Returns the new board rotation value.
     */
    @Function
    int rotateBoard(int positions) {
        boardRotation = (boardRotation + positions) % 360
        return boardRotation
    }
}
