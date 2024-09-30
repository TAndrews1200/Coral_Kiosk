package com.coral.coral_kiosk.detailsFragment;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coral.coral_kiosk.CoralApp;
import com.coral.coral_kiosk.R;
import com.coral.coral_kiosk.baseClasses.BaseFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailsFragment extends BaseFragment {

    private DetailsViewModel mViewModel;

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        Button detailsButton = view.findViewById(R.id.detailsToCartButton);
        TextView itemName = view.findViewById(R.id.detailsFragmentItemName);
        TextView itemDescription = view.findViewById(R.id.detailsFragmentItemDescription);
        TextView itemPrice = view.findViewById(R.id.detailsFragmentItemPrice);

        EditText itemQuantity = view.findViewById(R.id.detailsFragmentQuantityToAdd);
        Button addToCartButton = view.findViewById(R.id.detailsFragmentAddToCartButton);

        mViewModel.setCurrentItem(DetailsFragmentArgs.fromBundle(getArguments()).getSelectedItem());

        itemName.setText(mViewModel.getCurrentItem().getName());
        itemDescription.setText(mViewModel.getCurrentItem().getDescription());
        itemPrice.setText("$" + mViewModel.getCurrentItem().getPrice());
        detailsButton.setOnClickListener(v -> navToCart());
        addToCartButton.setOnClickListener(v -> {
            String quantity = itemQuantity.getText().toString();
            Log.i("MARKED Ω", "Quantity Value: " + quantity);
            if (!quantity.isEmpty()) {
                mViewModel.addToCart(Integer.parseInt(quantity));
                Toast.makeText(this.getContext(), "Added " + quantity + " to cart!", Toast.LENGTH_SHORT).show();
                sendPurchaseNotification();
                navBack();
            } else {
                Toast.makeText(this.getContext(), "Please enter a valid quantity", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void navToCart() {
        findNavController(this).navigate(R.id.action_detailsFragment_to_cartFragment);
    }

    public void navBack() {
        findNavController(this).popBackStack();
    }

    /**
     * Sends a simple notification that you have added something to the cart.
     */
    public void sendPurchaseNotification() {
        /*
        I have heard arguments both for doing this sort of thing the simple way implemented here, or
        by picking up on signals sent out by the ViewModel. The simple approach is much more readable,
        while the ViewModel approach is better if the VM in question is being used for other views

        As this is an "Additional Feature" I've opted to take the simpler approach.
        */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.requireContext(), CoralApp.getCART_CHANNEL_ID())
                .setSmallIcon(R.drawable.baseline_shopping_cart_24)
                .setContentTitle("Item Added")
                .setContentText("The " + mViewModel.getCurrentItem().getName() + " has been added to the cart")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        ((NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(CoralApp.getCART_NOTIFICATION_ID(), builder.build());
    }
}