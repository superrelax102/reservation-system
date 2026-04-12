INSERT INTO menu (name, detail, duration, currentfee) VALUES ('パーマ', 'くるくるにします', 90, 9000);
INSERT INTO menu (name, detail, duration, currentfee) VALUES ('カット', '丸坊主にします', 60, 3000);
INSERT INTO menu (name, detail, duration, currentfee) VALUES ('シャンプー', 'キューティクル奪います', 30, 1500);
INSERT INTO reservation (startat, endat, status, userid, staffid, menuid, billingfee, createdat, isdeleted) VALUES ('2026-04-16 10:00:00', '2026-04-16 11:00:00', '予約中', 999, 999, 999, 3000, '2026-04-11 11:00:00', false);
INSERT INTO reservation (startat, endat, status, userid, staffid, menuid, billingfee, createdat, isdeleted) VALUES ('2026-04-16 15:00:00', '2026-04-16 17:00:00', '予約中', 999, 999, 999, 3000, '2026-04-11 10:00:00', false);
INSERT INTO reservation (startat, endat, status, userid, staffid, menuid, billingfee, createdat, isdeleted) VALUES ('2026-04-17 15:00:00', '2026-04-17 17:00:00', '予約中', 999, 999, 999, 3000, '2026-04-12 13:00:00', false);