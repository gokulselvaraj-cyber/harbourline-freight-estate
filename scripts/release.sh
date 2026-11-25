#!/usr/bin/env bash
# Tag a service release: scripts/release.sh booking-service 4.6.3
set -euo pipefail
svc="$1"; ver="$2"
git tag -a "${svc}-v${ver}" -m "${svc} ${ver}"
git push origin "${svc}-v${ver}"
echo "Now run the deploy-prod workflow with service=${svc%%-*} version=${ver}"
