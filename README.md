# Coral Kiosk

Coral Kiosk is a Demo App made to simulate a very basic commerce app. The simplest, most straightforward flow is as follows:

1) Enter the app
2) Accept the Permissions (The app is not equipped with robust permissions flows; See the PAQ)
3) See the item list (Items within 10km of your location are highlighted in blue; See the PAQ for more.)
4) Select an item
5) Type in a quantity to the Quantity field
6) Add to Cart
7) See the Notification, as well as the toast.
8) Repeat 4-7 as desired (You should probably have two types of item in your cart)
9) Go to Cart
10) Try deleting an item
11) See the total change.
12) Select the option to Buy
13) See the checkout confirmation (This will summarize the number of items ordered, and their total cost.
14) Confirm your purchase, and see a toast reconfirming the number.
15) Return to the cart to see it empty.

## PAQ (Potentially Asked Questions)
Q: Why isn't the Permissions Flow more robust?
A: Quite simply, in my experience there's a lot of approaches to permissions, ranging from something not far from what you see here to multi-screened affairs consisting of core permission fragments and multiple variants of interstitials to handle rejection. Generally I believe that the Permissions Flow is very much a question for Product/Design, and in my experience it's generally a more costly thing in terms of dev time. Ultimately there was nothing requesting a desire to specifically see a Permissions Flow so I opted for something more utilitarian to let the Demo App be a Demo.

Q: Why aren't any of the items highlighted?
A: In short, I wasn't sure where any given user might be, so setting up items for the highlighter would have been difficult. However, setting an item to your location should be quite easy. Just Ctrl+Shift+F "Custom Fit Item" and you'll fine the test item list. If your device is surprisingly inaccurate, you can also search for HIGHLIGHT_RANGE, but the default of 10km should be fine. After all, your devices coordinates will be easy to find on the List page when you look!
