import * as React from 'react';
import { render, RenderResult } from '@testing-library/react';
import VerticalLinearStepper from './Stepper';
import { act } from 'react-dom/test-utils';
import sseMock from './EventSourceMock.js';

function* mockedReponses() {
  yield {data:'{"dates":[{"key":{"day":1,"period":1,"year":-35},"value":"Active"},{"key":{"day":2,"period":1,"year":-35},"value":"None"},{"key":{"day":3,"period":1,"year":-35},"value":"None"},{"key":{"day":4,"period":1,"year":-35},"value":"None"},{"key":{"day":5,"period":1,"year":-35},"value":"None"}]}'}
  yield {data:'{"dates":[{"key":{"day":1,"period":1,"year":-35},"value":"Completed"},{"key":{"day":2,"period":1,"year":-35},"value":"Active"},{"key":{"day":3,"period":1,"year":-35},"value":"None"},{"key":{"day":4,"period":1,"year":-35},"value":"None"},{"key":{"day":5,"period":1,"year":-35},"value":"None"}]}'}
  yield {data:'{"dates":[{"key":{"day":1,"period":1,"year":-35},"value":"Completed"},{"key":{"day":2,"period":1,"year":-35},"value":"Completed"},{"key":{"day":3,"period":1,"year":-35},"value":"Active"},{"key":{"day":4,"period":1,"year":-35},"value":"None"},{"key":{"day":5,"period":1,"year":-35},"value":"None"}]}'}
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