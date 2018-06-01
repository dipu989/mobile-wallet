package org.mifos.mobilewallet.mifospay.passcode.presenter;

import org.mifos.mobilewallet.core.base.UseCaseHandler;
import org.mifos.mobilewallet.core.data.fineract.api.FineractApiManager;
import org.mifos.mobilewallet.core.domain.usecase.AuthenticateUser;
import org.mifos.mobilewallet.core.domain.usecase.FetchClientData;
import org.mifos.mobilewallet.mifospay.base.BaseView;
import org.mifos.mobilewallet.mifospay.data.local.PreferencesHelper;
import org.mifos.mobilewallet.mifospay.passcode.PassCodeContract;

import javax.inject.Inject;

/**
 * Created by ankur on 15/May/2018
 */
public class PassCodePresenter implements PassCodeContract.PassCodePresenter {
    private PassCodeContract.PassCodeView mPassCodeView;
    private final UseCaseHandler mUsecaseHandler;

    private final PreferencesHelper preferencesHelper;

    @Inject
    AuthenticateUser authenticateUserUseCase;

    @Inject
    FetchClientData fetchClientDataUseCase;

    @Inject
    public PassCodePresenter(UseCaseHandler useCaseHandler, PreferencesHelper preferencesHelper) {
        this.mUsecaseHandler = useCaseHandler;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public void attachView(BaseView baseView) {
        mPassCodeView = (PassCodeContract.PassCodeView) baseView;
        mPassCodeView.setPresenter(this);
    }

    public void createAuthenticatedService() {
        FineractApiManager.createSelfService(preferencesHelper.getToken());
    }
}