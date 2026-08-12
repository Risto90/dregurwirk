#!/bin/bash
# Turn a failed suite log into GitHub Actions error annotations.
#
# Raw job logs are not always reachable, so a failing run has to explain itself
# through annotations. Print the lines the failing check emitted just before the
# runner declared it failed, dropping the ALSA warnings that CI runners produce
# because they have no sound card.
LOG="$1"
[ -f "$LOG" ] || exit 0

CLEAN=$(mktemp)
grep -avE "ALSA|snd_config|snd_func|_snd_|pcm_|^[[:space:]]*$" "$LOG" > "$CLEAN"

LINE=$(grep -an "FAIL: " "$CLEAN" | tail -n 1 | cut -d: -f1)
if [ -n "$LINE" ]; then
    START=$(( LINE > 9 ? LINE - 9 : 1 ))
    sed -n "${START},${LINE}p" "$CLEAN"
else
    tail -n 10 "$CLEAN"
fi | while IFS= read -r line; do
    echo "::error::${line}"
done

rm -f "$CLEAN"
