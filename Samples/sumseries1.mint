x=10;
a = sumseries(x);
print('Summation of x ( 1 .. ' + x + ') = ' + a);
n = 2;
a = sumseriesn(10,n);
print('Summation of x^' + n + ' ( 1 .. ' + x + ') = ' + a);

a = isprime(50);
b = isprime(31);
print( 'isprime(' + 50 + ')=' + a );
print( 'isprime(' + 31 + ')=' + b );
c = primes(100);
print('List of primes upto 100');
print(c);

a = fibo(10);
a = fibolist(10);print(a);