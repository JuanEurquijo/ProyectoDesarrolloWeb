import { Route } from "./route";
import { Schedule } from "./schedule";

export class Bus {
  public id:number | undefined;
  public assignedRoutes:Route[];
  constructor(
    public plate:string,
    public model:string) {};

}
