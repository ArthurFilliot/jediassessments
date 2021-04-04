import * as React from 'react';
import { render, RenderResult } from '@testing-library/react';
import VerticalLinearStepper from './Stepper';

let documentBody;

describe('<VerticalLinearStepper />', () => {
  beforeEach(() => {
    documentBody = render(<VerticalLinearStepper />);
  });
  it('shows', () => {
    expect(documentBody.getByText('Select campaign settings')).toBeInTheDocument;
    // expect(documentBody.getByAltText('picachu')).toBeInTheDocument();
    // expect(documentBody.getByText('Maybe internet connection is not good.')).toBeInTheDocument();
  });
});