1)Clothing and reviews have the different endpoints. There is one to many relationship between clothing and reviews.
lazy LAZY has been used for performance.

2) pagination is used in API for performance

3) spring webflux & netty are used for performance, it's not complete ones because spring data mysql's reactive spring data needs the extra efforts in the current spring data version. in the future, netty, webflux, and reactive spring data should serve better.

4) index is needed in mysql for the performance. 
