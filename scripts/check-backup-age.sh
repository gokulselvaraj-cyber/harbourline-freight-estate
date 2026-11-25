#!/usr/bin/env bash
# Prints hours since the last booking dump in the backup bucket.
set -euo pipefail
BUCKET="${PG_BACKUP_BUCKET:?}"
last=$(aws s3 ls "$BUCKET/booking/" | sort | tail -1 | awk '{print $1" "$2}')
echo $(( ( $(date +%s) - $(date -d "$last" +%s) ) / 3600 ))
