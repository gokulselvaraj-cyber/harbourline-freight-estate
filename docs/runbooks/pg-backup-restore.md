# Runbook: pg backup restore

Backups run nightly at 01:30 (`roles/pg_backup`). Restore with `playbooks/restore-db.yml`.
Test a restore every quarter.
