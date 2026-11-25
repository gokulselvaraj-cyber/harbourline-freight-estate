# ADR 0005: Ansible on VMs, not Kubernetes

Status: Accepted (2024-01)

## Decision
Eleven servers plus depot boxes do not justify a cluster. Ansible builds everything from `site.yml`.
