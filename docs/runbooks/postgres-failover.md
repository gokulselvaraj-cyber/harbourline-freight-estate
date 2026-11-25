# Runbook: postgres failover

hf-db-01 is a single primary with WAL shipping to the standby. Promote the standby with
`pg_ctl promote`, then change `db_host` in group_vars/prod.yml and redeploy all three services.
