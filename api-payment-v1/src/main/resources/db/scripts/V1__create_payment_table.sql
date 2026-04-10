-- ============================================================
-- V1 : Create payment table
-- ============================================================

CREATE TABLE IF NOT EXISTS payment (
    id             UUID          NOT NULL,
    payee_type     VARCHAR(20)   NOT NULL,
    amount         NUMERIC(15,2) NOT NULL,
    payment_method VARCHAR(20)   NOT NULL,
    payment_date   DATE          NOT NULL,
    transaction_id VARCHAR(100),
    state          VARCHAR(20)   NOT NULL DEFAULT 'CREATED',
    CONSTRAINT pk_payment PRIMARY KEY (id),
    CONSTRAINT chk_payment_amount CHECK (amount > 0)
);

CREATE INDEX IF NOT EXISTS idx_payment_state  ON payment(state);
CREATE INDEX IF NOT EXISTS idx_payment_date   ON payment(payment_date);
