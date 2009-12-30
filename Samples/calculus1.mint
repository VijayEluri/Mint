/*differntial calculus */

y='x^2';
wrt='x';

a = dx(y,wrt);
print('Differential:' + a);

/* example for eval function */
b=eval('x=5;c=' + a + ';return c;');
print(b);

/*integral calculus */
z = int(y,wrt);
print('Integral:' + z);