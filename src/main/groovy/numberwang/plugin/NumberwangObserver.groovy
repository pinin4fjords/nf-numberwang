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
    private boolean german
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

    private static final List<String> GERMAN_OPPONENTS = [
        'Hans', 'Greta', 'Kolosson', 'Dieter',
        'der Erzbischof von K\u00f6ln',
        'ein Pferd',
        'deine Oma',
        'der Begriff von Donnerstag',
        'ein verd\u00e4chtiges Glas Marmelade',
        'ein zunehmend nerv\u00f6ser Dachs',
        'die Nummer 4 (verkleidet)',
        'eine heimgesuchte Tabellenkalkulation',
        'Bismarcks Schnurrbart',
        'zwei Otter in einem Trenchcoat',
        'ein abtr\u00fcnniges Semikolon',
        'die gesamte Bev\u00f6lkerung von Bielefeld',
        'ein zutiefst verwirrter Flamingo',
        'dein Abteilungsleiter',
        'eine empfindungsf\u00e4hige Pipeline',
        'ein Pinguin mit einem Groll',
        'das WLAN-Passwort',
        'ein beh\u00f6rdlich genehmigter Kreisverkehr',
        'drei Bienen mit einer Meinung',
        'ein sehr alter Joghurt',
        'das Konzept der Negativzinsen',
        'die Katze von jemand anderem',
        'ein verfluchtes PDF',
        'die Leere',
        'eine \u00fcberraschend selbstbewusste R\u00fcbe',
        'ein stillgelegter Briefkasten',
        'dein Kinderzahnarzt',
        'eine tragende Taube',
        'der Geist einer veralteten API',
        'ein Hund der zu viel gesehen hat',
        'ein ungel\u00f6ster Merge-Konflikt',
        'die R\u00fcckseite des Sofas',
        'eine empfindungsf\u00e4hige Datenschutzerkl\u00e4rung',
        'eine Herde mittlerer Manager',
        'die Nummer 37 (Titelverteidiger)',
        'ein heimgesuchter Docker-Container',
        'jemand der in der 6. Klasse seinen H\u00f6hepunkt hatte',
        'ein passiv-aggressiver Klebezettel',
        'ein Lama in Business-Casual',
        'die A1 um 17 Uhr',
        'ein Glas Wespen mit Meinungen',
        'dein Mathelehrer aus der Neunten',
        'ein mysteri\u00f6s warmer Stuhl',
        'das Eszett (\u00df) (umstritten)',
        'ein leicht feuchtes Handtuch',
        'eine besorgt aussehende Wolke',
        'die Steuerererkl\u00e4rung',
        'ein philosophischer Schrebergarten'
    ]

    private static final List<String> GERMAN_PRESENTERS = [
        'Hans', 'Greta', 'Kolosson',
        'der Geist von N\u00fcmberwang Vergangenheit',
        'ein Roboter der die Regeln nicht versteht',
        'jemand der von der Stra\u00dfe hereingewandert ist',
        'ein zutiefst unqualifizierter Praktikant',
        'eine sprechende Waage',
        'das Konzept der Regeln (locker angewandt)',
        'ein defekter Teleprompter'
    ]

    NumberwangObserver(boolean rotateBoard, boolean german) {
        this.rotateBoard = rotateBoard
        this.german = german
    }

    @Override
    void onFlowCreate(Session session) {
        def opponentPool = german ? GERMAN_OPPONENTS : OPPONENTS
        def presenterPool = german ? GERMAN_PRESENTERS : PRESENTERS
        opponent = opponentPool[rng.nextInt(opponentPool.size())]
        presenter = presenterPool[rng.nextInt(presenterPool.size())]

        if (german) {
            println ''
            println '  🎰✨ =============================== ✨🎰'
            println '        🔢 Willkommen bei N\u00dcMBERWANG! 🔢'
            println "        🥊 Du gegen ${opponent}"
            println "        🎙️  Moderator: ${presenter}"
            println '  🎰✨ =============================== ✨🎰'
            println ''
        } else {
            println ''
            println '  🎰✨ =============================== ✨🎰'
            println '        🔢 Welcome to NUMBERWANG! 🔢'
            println "        🥊 You vs ${opponent}"
            println "        🎙️  Host: ${presenter}"
            println '  🎰✨ =============================== ✨🎰'
            println ''
        }
    }

    @Override
    void onProcessPending(TaskHandler handler, TraceRecord trace) {
        taskCount++
        boolean isNw = rng.nextInt(5) < 2

        if (isNw) {
            boolean youScore = rng.nextBoolean()
            if (youScore) yourScore++ else opponentScore++
            def you = german ? 'Du' : 'You'
            def scorer = youScore ? you : opponent
            def processName = handler.task.processor.name
            int idx = handler.task.index
            def nwLabel = german ? 'N\u00dcMBERWANG' : 'NUMBERWANG'

            try {
                def verb
                if (german) {
                    verb = (scorer == 'Du') ? 'punktest' : 'punktet'
                } else {
                    verb = (scorer == 'You') ? 'score' : 'scores'
                }
                handler.task.name = "${processName} (${idx} 🔢✨ ${nwLabel}! ${scorer} ${verb}!)"
                gameLog.add([task: "${processName} (${idx})", scorer: scorer, you: yourScore, them: opponentScore])
            } catch (Exception e) {
                gameLog.add([task: "${processName} (${idx})", scorer: scorer, you: yourScore, them: opponentScore])
            }
        }

        if (rotateBoard && taskCount > 0 && taskCount % 7 == 0) {
            int temp = yourScore
            yourScore = opponentScore
            opponentScore = temp
            def rotateMsg = german ? '🌀🌀 DAS BRETT DREHEN! 🌀🌀' : '🌀🌀 ROTATE THE BOARD! 🌀🌀'
            gameLog.add([task: rotateMsg, scorer: null, you: yourScore, them: opponentScore])
        }
    }

    @Override
    void onFlowComplete() {
        def you = german ? 'Du' : 'You'
        def nwName = german ? 'N\u00fcmberwang' : 'Numberwang'
        def nwUpper = german ? 'N\u00dcMBERWANG' : 'NUMBERWANG'

        println ''
        println '  🏆🎰 =============================== 🎰🏆'
        println "            📊 ${nwName} ${german ? 'Ergebnisse' : 'Results'} 📊"
        println '  🏆🎰 =============================== 🎰🏆'

        if (gameLog.size() > 0) {
            println ''
            for (entry in gameLog) {
                if (entry.scorer) {
                    def verb
                    if (german) {
                        verb = (entry.scorer == 'Du') ? 'punktest' : 'punktet'
                    } else {
                        verb = (entry.scorer == 'You') ? 'score' : 'scores'
                    }
                    println "    💥 ${entry.task} → ${entry.scorer} ${verb}! [${you} ${entry.you}-${entry.them} ${opponent}]"
                } else {
                    println "    ${entry.task} [${you} ${entry.you}-${entry.them} ${opponent}]"
                }
            }
        }

        println ''
        println "    🧑 ${you}: ${yourScore}"
        println "    👾 ${opponent}: ${opponentScore}"
        println ''

        if (yourScore > opponentScore) {
            if (german) {
                println "    🎉🎉🎉 DU GEWINNST! Das ist ${nwUpper}! 🎉🎉🎉"
            } else {
                println "    🎉🎉🎉 YOU WIN! That's Numberwang! 🎉🎉🎉"
            }
        } else if (opponentScore > yourScore) {
            if (german) {
                println "    😭💀 Du verlierst! ${opponent} gewinnt das heutige ${nwName}. 💀😭"
            } else {
                println "    😭💀 You lose! ${opponent} wins today's Numberwang. 💀😭"
            }
        } else {
            if (german) {
                println "    🤝✨ Unentschieden! Das ist ${nwUpper}! ✨🤝"
            } else {
                println "    🤝✨ It's a draw! That's Numberwang! ✨🤝"
            }
        }

        println '  🏆🎰 =============================== 🎰🏆'
        println ''
    }

    private String scoreline() {
        def you = german ? 'Du' : 'You'
        return "[${you} ${yourScore}-${opponentScore} ${opponent}]"
    }
}
