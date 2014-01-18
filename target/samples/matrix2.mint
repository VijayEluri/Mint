/* example of matrix functions*/
a = matrix([1,2,3;4,5,6]);
print(a);

a=set(a,1,2,10);
print(a);

a = matrix(random(3,3));
print(a);
print(det(a));
a = transpose(a);
print('Transpose:\n' + a);

b = matrix(random(3,3));

c = solve(a,b);
print(c);

print(a + b);
print(a - b);
print(a * b);
print(a / b);

print(a + 2);
print(a - 2);
print(a * 2);
print(a / 2);


print(low(a));
print(up(a));

a = eye(3,3);
a = getD(a);
print(a);

b = size(a);
print('size of:' + b);

a = matrix([1,2,3;4,5,6;7,8,9]);
print(a);