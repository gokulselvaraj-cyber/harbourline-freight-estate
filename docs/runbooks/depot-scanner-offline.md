# Runbook: depot scanner offline

Scanners at a depot show OFFLINE. SSH to `hf-dep-<code>-01`, `systemctl status hf-depot-scanner`.
The bridge listens on 7010. Restart; if the box is down, depots fall back to paper manifests.
