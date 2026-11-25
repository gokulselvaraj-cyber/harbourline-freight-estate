# Runbook: dlq replay

Dead letters land on `hf.dlx`. Inspect in the management UI (15672), fix the cause, then shovel back
to the original queue.
