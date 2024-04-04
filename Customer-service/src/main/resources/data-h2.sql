insert into customers (customer_id, first_name, last_name, email, street_address, city, state, country, postal_code)
values('7f5cf6c6-031b-4e5b-bb28-f83b0b7c57b0', 'John', 'Doe', 'johndoe@gmail.com', '123 Main St', 'Anytown', 'California', 'United States', '12345');

insert into customers (customer_id, first_name, last_name, email, street_address, city, state, country, postal_code)
values('c796dc7e-67d9-4912-bd20-b543c1d9c5b7', 'Jane', 'Smith', 'janesmith@outlook.com', '456 Elm St', 'Othertown', 'New York', 'United States', '54321');

insert into customer_phonenumbers(customer_id, type, number)
values(1, 'PERSONEl', '310-555-4321');

insert into customer_phonenumbers(customer_id, type, number)
values(2, 'HOME', '415-555-1234');