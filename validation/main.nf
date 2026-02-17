#!/usr/bin/env nextflow

include { isNumberwang } from 'plugin/nf-numberwang'
include { wangernumb } from 'plugin/nf-numberwang'

process PLAY_NUMBERWANG {
    input:
        val number
    output:
        val number
    exec:
    true
}

process WANGERNUMB_ROUND {
    input:
        val number
    output:
        val result
    exec:
    result = wangernumb(number)
}

workflow {
    numbers_ch = channel.of(1, 2, 3, 7, 14, 22, 42, 73, 99, 100)

    PLAY_NUMBERWANG(numbers_ch)
    WANGERNUMB_ROUND(numbers_ch)
}
