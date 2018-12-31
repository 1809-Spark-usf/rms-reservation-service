INSERT INTO campuses (id, name) VALUES ('1', 'IT University');
INSERT INTO campuses (id, name) VALUES ('2', 'USF');

INSERT INTO buildings (id, name, campus_id) VALUES ('1', 'Main Office', '1');
INSERT INTO buildings (id, name, campus_id) VALUES ('2', 'Library', '1');
INSERT INTO buildings (id, name, campus_id) VALUES ('3', 'Room 404', '2');
INSERT INTO buildings (id, name, campus_id) VALUES ('4', 'Cubicle 9', '2');
INSERT INTO buildings (id, name, campus_id) VALUES ('5', 'The BNKR', '2');

INSERT INTO resources (id, type, building_id, name, disabled, inactive, retired, available_start_date, reservable_after, reservable_before, has_computer, number_of_outlets, has_microphone, has_ethernet) VALUES
('1', 0, '1', 'Block L', 'false', 'true', 'false', '2007-12-03T10:15:30', '2007-12-03T10:15:30', '2007-12-03T10:15:30', 'true', '7', 'true', 'false');

INSERT INTO resources (id, type, building_id, name, disabled, inactive, retired, available_start_date, reservable_after, reservable_before, has_computer, number_of_outlets, has_microphone, has_ethernet) VALUES
('2', 0, '4', 'Block E', 'false', 'true', 'true', '2007-12-03T10:15:30', '2007-12-03T10:15:30', '2007-12-03T10:15:30', 'true', '903', 'true', 'true');

INSERT INTO resources (id, type, building_id, name, disabled, inactive, retired, available_start_date, reservable_after, reservable_before, has_computer, number_of_outlets, has_microphone, has_ethernet) VALUES
('3', 1, '5', 'Block G', 'true', 'true', 'false', '2007-12-03T10:15:30', '2007-12-03T10:15:30', '2007-12-03T10:15:30', 'false', '1', 'false', 'false');

INSERT INTO resources (id, type, building_id, name, disabled, inactive, retired, available_start_date, reservable_after, reservable_before, has_computer, number_of_outlets, has_microphone, has_ethernet) VALUES
('4', 1, '2', 'Block S', 'false', 'false', 'false', '2007-12-03T10:15:30', '2007-12-03T10:15:30', '2007-12-03T10:15:30', 'false', '30', 'false', 'false');

INSERT INTO resources (id, type, building_id, name, disabled, inactive, retired, available_start_date, reservable_after, reservable_before, has_computer, number_of_outlets, has_microphone, has_ethernet) VALUES
('5', 1, '3', 'Block S', 'true', 'true', 'true', '2007-12-03T10:15:30', '2007-12-03T10:15:30', '2007-12-03T10:15:30', 'true', '1000', 'false', 'true');

