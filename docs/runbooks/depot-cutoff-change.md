# Runbook: depot cutoff change

Cut-off times are in host_vars (`dispatch_cutoff`) and in the `depot` table. Change both, and the
`ManifestCloseJob` cron in booking-service.
