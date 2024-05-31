INSERT INTO CUST (CUST_ID, CUST_NAME, BIRTHDAY, NATION, WLF_YN, WLF_DV_CD)
SELECT * FROM (SELECT '0000000001' as CUST_ID, 'John Doe' as CUST_NAME, '900101' as BIRTHDAY, 'US' as NATION, NULL as WLF_YN, NULL as WLF_DV_CD UNION ALL
               SELECT '0000000002', 'Jane Smith', '850515', 'CA', NULL, NULL UNION ALL
               SELECT '0000000003', 'Michael Johnson', '780722', 'US', NULL, NULL UNION ALL
               SELECT '0000000004', 'Emily Davis', '920905', 'GB', NULL, NULL UNION ALL
               SELECT '0000000005', 'Robert Brown', '801130', 'AU', NULL, NULL UNION ALL
               SELECT '0000000006', 'KimJungIl', '311202', 'KP', NULL, NULL UNION ALL
               SELECT '0000000007', 'ParkMinSoo', '870304', 'KR', NULL, NULL UNION ALL
               SELECT '0000000008', 'Alice Johnson', '910611', 'US', NULL, NULL UNION ALL
               SELECT '0000000009', 'Bob Lee', '830423', 'KR', NULL, NULL UNION ALL
               SELECT '0000000010', 'Charlie Kim', '750819', 'KR', NULL, NULL UNION ALL
               SELECT '0000000011', 'David Miller', '891112', 'US', NULL, NULL UNION ALL
               SELECT '0000000012', 'Eva Brown', '810103', 'GB', NULL, NULL UNION ALL
               SELECT '0000000013', 'Frank Moore', '930727', 'CA', NULL, NULL UNION ALL
               SELECT '0000000014', 'Grace Kim', '901217', 'KR', NULL, NULL UNION ALL
               SELECT '0000000015', 'Hannah White', '870509', 'US', NULL, NULL UNION ALL
               SELECT '0000000016', 'Irene Martinez', '921030', 'US', NULL, NULL UNION ALL
               SELECT '0000000017', 'Jack Wilson', '840914', 'CA', NULL, NULL UNION ALL
               SELECT '0000000018', 'Kelly Zhang', '950321', 'CN', NULL, NULL UNION ALL
               SELECT '0000000019', 'Leo Clark', '860702', 'US', NULL, NULL UNION ALL
               SELECT '0000000020', 'Mia Davis', '880225', 'GB', NULL, NULL
              ) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM CUST);
