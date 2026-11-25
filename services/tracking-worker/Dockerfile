FROM python:3.11-slim
WORKDIR /app
COPY pyproject.toml ./
COPY tracking_worker ./tracking_worker
RUN pip install --no-cache-dir .
EXPOSE 9102
USER 10001
CMD ["hf-tracking-worker"]
