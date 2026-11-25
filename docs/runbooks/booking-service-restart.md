# Runbook: booking service restart

Restart booking-service on one app server at a time:
`ansible-playbook -i inventories/prod/hosts.ini playbooks/deploy-booking.yml -l hf-app-01.hf.internal`.
Check `/actuator/health` on port 8080.
