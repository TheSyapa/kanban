import {Card} from '../card/card';
import {Dev} from '../dev/dev';

export class Project {

  id: number;
  name: string;
  cards: Card[];
  devs: Dev[];
}
