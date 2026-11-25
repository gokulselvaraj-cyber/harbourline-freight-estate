# Runbook: tracking eta backlog

If `tracking.carrier-events` has more than 5,000 ready messages, scale `CONCURRENCY` to 8 in
`group_vars/app_servers.yml` (`tracking_concurrency`) and redeploy tracking.
