package numberwang.plugin

import nextflow.Session
import nextflow.processor.TaskHandler
import nextflow.trace.TraceObserver
import nextflow.trace.TraceRecord

/**
 * Trace observer that runs a game of Numberwang between
 * you and a randomly chosen opponent across your pipeline run.
 *
 * Annotates the standard Nextflow task display so you can
 * see who scores each point. Win or lose at the end.
 */
class NumberwangObserver implements TraceObserver {

    private int taskCount = 0
    private int yourScore = 0
    private int opponentScore = 0
    private boolean rotateBoard
    private Random rng = new Random()
    private String opponent
    private String presenter
    private List<Map> gameLog = []

    private static final List<String> OPPONENTS = [
        'Simon', 'Julie', 'Colosson', 'Robert',
        'the Archbishop of Canterbury',
        'a horse',
        'your nan',
        'the concept of Thursday',
        'a suspicious jar of marmalade',
        'an increasingly nervous badger',
        'the number 4 (in disguise)',
        'a haunted spreadsheet',
        'Margaret Thatcher\'s ghost',
        'two otters in a trenchcoat',
        'a rogue semicolon',
        'the entire population of Swindon',
        'a deeply confused flamingo',
        'your line manager',
        'a sentient pipeline',
        'a penguin with a grudge',
        'the WiFi password',
        'a council-approved roundabout',
        'three bees who share one opinion',
        'a very old yoghurt',
        'the concept of negative equity',
        'someone else\'s cat',
        'a cursed PDF',
        'the void',
        'a surprisingly confident turnip',
        'a decommissioned postbox',
        'your childhood dentist',
        'a load-bearing pigeon',
        'the ghost of a deprecated API',
        'a dog who has seen too much',
        'an unresolved merge conflict',
        'the back of the sofa',
        'a sentient Terms and Conditions',
        'a flock of middle managers',
        'the number 37 (returning champion)',
        'a haunted Docker container',
        'someone who peaked in Year 6',
        'a passive-aggressive Post-it note',
        'a llama in business casual',
        'the M25 at 5pm',
        'a jar of wasps with opinions',
        'your Year 9 maths teacher',
        'a mysteriously warm chair',
        'the Oxford comma (controversial)',
        'a slightly damp towel',
        'a worried-looking cloud'
    ]

    private static final List<String> PRESENTERS = [
        'Simon', 'Julie', 'Colosson',
        'the ghost of Numberwang past',
        'a robot who doesn\'t understand the rules',
        'someone who wandered in off the street',
        'a deeply unqualified intern',
        'a speak-your-weight machine',
        'the concept of rules (loosely applied)',
        'a malfunctioning autocue'
    ]

    NumberwangObserver(boolean rotateBoard) {
        this.rotateBoard = rotateBoard
    }

    @Override
    void onFlowCreate(Session session) {
        opponent = OPPONENTS[rng.nextInt(OPPONENTS.size())]
        presenter = PRESENTERS[rng.nextInt(PRESENTERS.size())]

        println ''
        println '  🎰✨ =============================== ✨🎰'
        println '        🔢 Welcome to NUMBERWANG! 🔢'
        println "        🥊 You vs ${opponent}"
        println "        🎙️  Host: ${presenter}"
        println '  🎰✨ =============================== ✨🎰'
        println ''
    }

    @Override
    void onProcessPending(TaskHandler handler, TraceRecord trace) {
        taskCount++
        boolean isNw = rng.nextInt(5) < 2

        if (isNw) {
            boolean youScore = rng.nextBoolean()
            if (youScore) yourScore++ else opponentScore++
            def scorer = youScore ? 'You' : opponent
            def processName = handler.task.processor.name
            int idx = handler.task.index

            try {
                def verb = (scorer == 'You') ? 'score' : 'scores'
                handler.task.name = "${processName} (${idx} 🔢✨ NUMBERWANG! ${scorer} ${verb}!)"
                gameLog.add([task: "${processName} (${idx})", scorer: scorer, you: yourScore, them: opponentScore])
            } catch (Exception e) {
                gameLog.add([task: "${processName} (${idx})", scorer: scorer, you: yourScore, them: opponentScore])
            }
        }

        if (rotateBoard && taskCount > 0 && taskCount % 7 == 0) {
            int temp = yourScore
            yourScore = opponentScore
            opponentScore = temp
            gameLog.add([task: '🌀🌀 ROTATE THE BOARD! 🌀🌀', scorer: null, you: yourScore, them: opponentScore])
        }
    }

    @Override
    void onFlowComplete() {
        println ''
        println '  🏆🎰 =============================== 🎰🏆'
        println '            📊 Numberwang Results 📊'
        println '  🏆🎰 =============================== 🎰🏆'

        if (gameLog.size() > 0) {
            println ''
            for (entry in gameLog) {
                if (entry.scorer) {
                    def verb = (entry.scorer == 'You') ? 'score' : 'scores'
                    println "    💥 ${entry.task} → ${entry.scorer} ${verb}! [You ${entry.you}-${entry.them} ${opponent}]"
                } else {
                    println "    ${entry.task} [You ${entry.you}-${entry.them} ${opponent}]"
                }
            }
        }

        println ''
        println "    🧑 You: ${yourScore}"
        println "    👾 ${opponent}: ${opponentScore}"
        println ''

        if (yourScore > opponentScore) {
            println "    🎉🎉🎉 YOU WIN! That's Numberwang! 🎉🎉🎉"
        } else if (opponentScore > yourScore) {
            println "    😭💀 You lose! ${opponent} wins today's Numberwang. 💀😭"
        } else {
            println "    🤝✨ It's a draw! That's Numberwang! ✨🤝"
        }

        println '  🏆🎰 =============================== 🎰🏆'
        println ''
    }

    private String scoreline() {
        return "[You ${yourScore}-${opponentScore} ${opponent}]"
    }
}
