# nf-numberwang

A Nextflow plugin for playing [Numberwang](https://www.youtube.com/watch?v=0obMRztklqU), the maths quiz that's simply everyone's cup of tea.

## Overview

For too long, computational pipeline developers have been forced to execute workflows without any indication of whether the numbers involved are, in fact, Numberwang. This represents a critical gap in the bioinformatics ecosystem.

**nf-numberwang** addresses this deficiency by providing a comprehensive, peer-reviewed\* Numberwang evaluation framework built on Colosson's formula as revised by Shinboner's theorem (4th edition, pp. 27-54). The plugin seamlessly integrates with any Nextflow pipeline to deliver real-time Numberwang adjudication, competitive scoring, and board rotation, directly within your standard pipeline output.

\*Reviewed by Colosson. Colosson was satisfied.

## Key Features

- **Live competitive Numberwang**: Every pipeline run is a match between you and a randomly selected opponent. Points are awarded according to the inscrutable rules of Numberwang as tasks are submitted
- **Inline task annotation**: Numberwang results appear directly in the standard Nextflow "Submitted process" output, providing an unprecedented level of integration between computational biology and light entertainment
- **Board rotation**: At regulation intervals, the board rotates and scores are exchanged, as per the official Numberwang rulebook (ISBN: pending, repeatedly rejected by publishers)
- **Custom functions**: Use `isNumberwang()`, `checkNumberwang()`, `wangernumb()`, and `rotateBoard()` directly in your pipeline logic for programmatic Numberwang access
- **51 carefully curated opponents**: Including but not limited to a suspicious jar of marmalade, the ghost of a deprecated API, an unresolved merge conflict, and the entire population of Swindon

## Installation

Add the plugin to your `nextflow.config`:

```groovy
plugins {
    id 'nf-numberwang@0.1.0'
}

numberwang {
    enabled = true
    rotateBoard = true
}
```

That's it. Your pipeline is now a gameshow.

## Usage

Simply run any Nextflow pipeline with the plugin enabled. No changes to your workflow code are required. The Numberwang observer will automatically:

1. Select your opponent from an internationally recognised pool of contestants
2. Appoint a qualified (or unqualified) presenter
3. Evaluate each submitted task for Numberwang validity
4. Award points according to rules that nobody fully understands
5. Rotate the board at the mandated interval
6. Declare a winner at pipeline completion

### Example Output

```
  🎰✨ =============================== ✨🎰
        🔢 Welcome to NUMBERWANG! 🔢
        🥊 You vs a haunted Docker container
        🎙️  Host: a robot who doesn't understand the rules
  🎰✨ =============================== ✨🎰

[a5/e6f00d] Submitted process > FASTQC (sample_1)
[14/ad1213] Submitted process > FASTQC (sample_2 🔢✨ NUMBERWANG! You score!)
[ac/70bed3] Submitted process > TRIM_GALORE (sample_1 🔢✨ NUMBERWANG! a haunted Docker container scores!)
...

  🏆🎰 =============================== 🎰🏆
            📊 Numberwang Results 📊
  🏆🎰 =============================== 🎰🏆

    💥 FASTQC (2) → You score! [You 1-0 a haunted Docker container]
    💥 TRIM_GALORE (1) → a haunted Docker container scores! [You 1-1 a haunted Docker container]
    🌀🌀 ROTATE THE BOARD! 🌀🌀 [You 1-1 a haunted Docker container]
    💥 MULTIQC (1) → You score! [You 2-1 a haunted Docker container]

    🧑 You: 2
    👾 a haunted Docker container: 1

    🎉🎉🎉 YOU WIN! That's Numberwang! 🎉🎉🎉
  🏆🎰 =============================== 🎰🏆
```

### One-liner

To play Numberwang with any pipeline without modifying config files:

```bash
nextflow run your/pipeline -plugins nf-numberwang@0.1.0 --numberwang.enabled=true --numberwang.rotateBoard=true
```

## Custom Functions

The plugin also provides functions for direct Numberwang evaluation within your pipeline code:

```groovy
include { isNumberwang } from 'plugin/nf-numberwang'
include { checkNumberwang } from 'plugin/nf-numberwang'
include { wangernumb } from 'plugin/nf-numberwang'
include { rotateBoard } from 'plugin/nf-numberwang'
```

| Function | Description | Returns |
|---|---|---|
| `isNumberwang(Number n)` | Determines whether `n` is Numberwang. Results are nondeterministic due to quantum Numberwang effects | `boolean` |
| `checkNumberwang(Number n)` | Returns the official adjudication as a formatted string | `String` |
| `wangernumb(Number n)` | The reverse round. Converts a number into its Wangernumb equivalent via the inverse Colosson transform | `Number` (0-99) |
| `rotateBoard(int positions)` | Rotates the board by the specified number of positions. This changes everything | `int` (new rotation) |

## Configuration

```groovy
numberwang {
    enabled = true         // Enable or disable the Numberwang experience
    rotateBoard = true     // Enable board rotation at regulation intervals
    boardRotation = 0      // Initial board rotation offset (advanced users only)
}
```

## Frequently Asked Questions

**Q: What are the rules of Numberwang?**
A: The rules of Numberwang are well-established and have been published in several volumes, none of which are available to the public. The plugin implements them faithfully.

**Q: Why did I lose to a penguin with a grudge?**
A: The penguin was better at Numberwang than you. This is not a reflection on you as a person, but it is a reflection on your Numberwang ability, which is poor.

**Q: Is this plugin appropriate for production genomics pipelines?**
A: We believe Numberwang enriches any computational context. Several unnamed institutions are rumoured to be evaluating nf-numberwang for clinical use, though none have confirmed this, and we suspect none exist.

**Q: The board rotation changed my score. Is this fair?**
A: The board rotation is mandated by the International Numberwang Commission (INC) and is not subject to appeal. All complaints should be directed to Colosson, who will not read them.

**Q: Can I choose my opponent?**
A: No. Your opponent is selected by fate, or by a pseudorandom number generator, which amounts to the same thing.

## Development

```bash
# Build the plugin
make build

# Run tests
make test

# Install locally
make install
```

## Contributing

Contributions are welcome, provided they do not compromise the mathematical integrity of Numberwang. All pull requests will be evaluated using Colosson's formula. Those that are Numberwang will be merged.

## License

Apache License 2.0

## Acknowledgements

Numberwang was created by Robert Webb and David Mitchell for the television programme "That Mitchell and Webb Look." This plugin is a loving tribute and is not affiliated with, endorsed by, or comprehensible to the original creators.

The Numberwang algorithm implemented herein is based on Colosson's formula as revised by Shinboner's theorem. Any resemblance to actual mathematics is coincidental.
