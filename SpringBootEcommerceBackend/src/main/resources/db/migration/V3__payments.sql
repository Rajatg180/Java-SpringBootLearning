
CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE, 
    status VARCHAR(32) NOT NULL,
    amount_cents BIGINT NOT NULL,
    idempotency_key VARCHAR(64) NOT NULL, -- idempotency_key is used to ensure that duplicate payment requests with the same key are not processed multiple times
    provider_ref VARCHAR(128), -- provider_ref is a reference from the payment provider, which can be used for reconciliation and tracking
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);


CREATE UNIQUE INDEX uq_payments_order_idem 
ON payments(order_id, idempotency_key);

CREATE INDEX idx_payments_order 
ON payments(order_id);