#!/usr/bin/env bash

echo $(curl -H "Content-Type: application/json" \
            -X POST \
            -d '{"variables": {"amount": {"value":5,"type":"long"}, "item": {"value":"item-xyz"} } }' \
            http://localhost:8080/engine-rest/process-definition/key/payment-retrieval/start)
