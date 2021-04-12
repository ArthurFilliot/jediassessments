import {galacticStandardCalendarReducer , updateFull, updatePartial} from './GalacticStandardCalendarStore'

function* mockedReponses() {
  yield {dates:[{"key":{"day":1,"period":1,"year":-35},"value":"1618257458000"},{"key":{"day":2,"period":1,"year":-35},"value":"1618257459000"},{"key":{"day":3,"period":1,"year":-35},"value":"1618257460000"},{"key":{"day":4,"period":1,"year":-35},"value":"1618257461000"}]}
  yield {dates:[{"key":{"day":6,"period":1,"year":-35},"value":"1618257463000"},{"key":{"day":7,"period":1,"year":-35},"value":"1618257464000"},{"key":{"day":8,"period":1,"year":-35},"value":"1618257465000"},{"key":{"day":9,"period":1,"year":-35},"value":"1618257466000"},{"key":{"day":10,"period":1,"year":-35},"value":"1618257467000"}]}
  yield {type:'end'};
};
let responsesGen = mockedReponses();

describe('galacticStandardCalendarReducer', () => {
  let logSpy
  beforeEach(() => {
    logSpy = jest.spyOn(console, 'debug');
  });
  it('should return the initial state', () => {
    expect(galacticStandardCalendarReducer(undefined, {}))
    .toEqual({
      galacticWindow: {},
      nowIndex: -1, 
    });

    let currWindow = responsesGen.next().value;
    let currClientNow = 1618257458955;

    updateFull(currWindow, currClientNow);
    expect(galacticStandardCalendarReducer(undefined, {}))
    .toEqual({
      galacticWindow: currWindow,
      nowIndex: 0,
    });

    currClientNow = 1618257459001;
    updatePartial(currClientNow);
    expect(galacticStandardCalendarReducer(undefined, {}))
    .toEqual({
      galacticWindow: currWindow,
      nowIndex: 1, 
    });

    currClientNow = 1618257460000;
    updatePartial(currClientNow);
    expect(galacticStandardCalendarReducer(undefined, {}))
    .toEqual({
      galacticWindow: currWindow,
      nowIndex: 2, 
    });

    currClientNow = 1618257464000;
    updatePartial(currClientNow);
    expect(galacticStandardCalendarReducer(undefined, {}))
    .toEqual({
      galacticWindow: currWindow,
      nowIndex: 3, 
    });

    currWindow = responsesGen.next().value;
    currClientNow = 1618257464000;
    updateFull(currWindow, currClientNow);
    expect(galacticStandardCalendarReducer(undefined, {}))
    .toEqual({
      galacticWindow: currWindow,
      nowIndex: 1, 
    });

  })
})

