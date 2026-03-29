-- ============================================================
-- V1 : Create refund_order and refund_bill tables
-- ============================================================

CREATE TABLE IF NOT EXISTS employee (
    id               BIGINT     GENERATED ALWAYS  AS IDENTITY  NOT NULL,
    user_id           BIGINT        NOT NULL,
    full_name         VARCHAR(100)  NOT NULL,
    immediate_supervisor_id        BIGINT        NOT NULL,
    position            VARCHAR(20)   NOT NULL,
    bank                VARCHAR(100),
    account_number       VARCHAR(20),
    CONSTRAINT pk_employee PRIMARY KEY (id)
);

