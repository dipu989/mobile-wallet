package org.mifos.mobilewallet.core.domain.usecase;

import org.mifos.mobilewallet.core.base.UseCase;
import org.mifos.mobilewallet.core.data.fineract.repository.FineractRepository;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ankur on 06/June/2018
 */

public class RunReport extends UseCase<RunReport.RequestValues, RunReport.ResponseValue> {

    private final FineractRepository mFineractRepository;

    @Inject
    public RunReport(FineractRepository fineractRepository) {
        mFineractRepository = fineractRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {

        mFineractRepository.getTransactionReceipt("PDF", requestValues.transactionId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getUseCaseCallback().onError(e.toString());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        getUseCaseCallback().onSuccess(new ResponseValue(responseBody));
                    }
                });
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final String transactionId;

        public RequestValues(String transactionId) {
            this.transactionId = transactionId;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final ResponseBody responseBody;

        public ResponseValue(ResponseBody responseBody) {
            this.responseBody = responseBody;
        }

        public ResponseBody getResponseBody() {
            return responseBody;
        }
    }
}
