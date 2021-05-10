import * as React from 'react';
import { render, RenderResult } from '@testing-library/react';
import VerticalLinearStepper from './Stepper';
import { act } from 'react-dom/test-utils';
import sseMock from './EventSourceMock.js';

function* mockedReponses() {
  yield {data:'{"1620074242314":"Atunda 1 Elona -35","1620074243314":"Katunda 2 Elona -35","1620074244314":"Satunda 3 Elona -35","1620074245314":"Datunda 4 Elona -35","1620074246314":"Natunda 5 Elona -35"}'}
  yield {data:'{"1620074247314":"Atunda 6 Elona -35","1620074248314":"Katunda 7 Elona -35","1620074249314":"Satunda 8 Elona -35","1620074250314":"Datunda 9 Elona -35","1620074251314":"Natunda 10 Elona -35"}'}
  yield {data:'{"1620074252314":"Atunda 11 Elona -35","1620074253314":"Katunda 12 Elona -35","1620074254314":"Satunda 13 Elona -35","1620074255314":"Datunda 14 Elona -35","1620074256314":"Natunda 15 Elona -35"}'}
  yield {type:'end'};
};
sseMock.responsesGen = mockedReponses();

describe('<VerticalLinearStepper />', () => {
  var documentBody
  let logSpy
  beforeEach(() => {
    logSpy = jest.spyOn(console, 'debug');
    window.history.pushState({}, '', 'http://localhost:80/ui');
    documentBody = render(<VerticalLinearStepper />);
  });
  it('shows', () => {
    expect(documentBody.getByText('Select campaign settings')).toBeInTheDocument;

    act(()=>sseMock.eventSource.fire());
    expect(logSpy).toBeCalledWith("VerticalLinearStepper Event fired");
    expect(logSpy).toBeCalledWith("VerticalLinearStepper activeIndex : 0");
    act(()=>sseMock.eventSource.fire());
    expect(logSpy).toBeCalledWith("VerticalLinearStepper activeIndex : 1");
    act(()=>sseMock.eventSource.fire());
    expect(logSpy).toBeCalledWith("VerticalLinearStepper activeIndex : 2");
  });
});