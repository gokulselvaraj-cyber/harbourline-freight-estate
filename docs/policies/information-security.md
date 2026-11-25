# Information security basics

- Secrets live in Ansible Vault (`vault_*` variables) and are injected as environment variables; nothing
  secret is committed.
- All internal traffic stays on the `10.40.0.0/16` network; the only public entry is the nginx edge.
- Personal data must not be written to logs (customs-docs redacts `shipTo.name` and `shipTo.address`).
- Access reviews: quarterly, by team leads.
