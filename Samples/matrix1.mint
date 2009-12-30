a = matrix([ 1, 2, 3; 4 , 5 , 6; 7, 8 , 9]);
b = matrix([ 1, 2, 3; 4 , 5 , 6; 7, 8 , 9]);
print(a);

c = a + b; print('A + B: \n ' + c);
c = a - b; print('A - B: \n ' + c);
c = a * b; print('A * B: \n ' + c);
c = a / b; print('A / B: \n ' + c);

b = 5.5;
c = a + b; print('A + B: \n ' + c);
c = a - b; print('A - B: \n ' + c);
c = a * b; print('A * B: \n ' + c);
c = a / b; print('A / B: \n ' + c);
c = a ^ b; print('A ^ B: \n ' + c);


a = eye(5);
b = eye(5,5);
print(a+b);